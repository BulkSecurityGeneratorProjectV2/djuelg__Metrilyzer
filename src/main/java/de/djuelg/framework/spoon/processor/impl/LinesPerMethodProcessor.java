package de.djuelg.framework.spoon.processor.impl;

import de.djuelg.domain.metric.impl.LinesPerMethodMetric;
import de.djuelg.domain.model.LinesPerMethod;
import de.djuelg.framework.spoon.processor.MetricProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;

public class LinesPerMethodProcessor extends MetricProcessor<CtMethod<?>> {

    private static final String SETTER_PREFIX = "set";
    private static final String GETTER_PREFIX = "get";
    private static final int HEAD_AND_TAIL_SIZE = 2;

    private final LinesPerMethodMetric metric;

    public LinesPerMethodProcessor() {
        this(new LinesPerMethodMetric());
    }

    public LinesPerMethodProcessor(LinesPerMethodMetric metric) {
        this.metric = metric;
    }

    @Override
    public void process(CtMethod<?> ctMethod) {
        if (isValid(ctMethod)) {
            String qualifiedClassName = ctMethod.getParent(CtClass.class).getQualifiedName();
            String methodName = ctMethod.getSimpleName();
            long lineCount = extractLineCount(ctMethod);
            metric.addDatapoint(new LinesPerMethod(qualifiedClassName, methodName, lineCount));
        }
    }

    private boolean isValid(CtMethod<?> method) {
        return method.getParent(CtClass.class) != null
                && !method.getModifiers().contains(ModifierKind.ABSTRACT)
                && !method.getSimpleName().startsWith(SETTER_PREFIX)
                && !method.getSimpleName().startsWith(GETTER_PREFIX);
    }

    private long extractLineCount(CtMethod<?> method) {
        return method.getBody().toString().split("\r\n|\r|\n").length - HEAD_AND_TAIL_SIZE;
    }

    @Override
    public LinesPerMethodMetric getMetric() {
        return metric;
    }
}
