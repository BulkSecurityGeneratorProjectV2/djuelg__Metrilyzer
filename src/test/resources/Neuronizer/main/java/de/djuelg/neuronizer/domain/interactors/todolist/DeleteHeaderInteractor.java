package de.djuelg.neuronizer.domain.interactors.todolist;

import de.djuelg.neuronizer.domain.interactors.base.Interactor;
import de.djuelg.neuronizer.domain.model.todolist.TodoListHeader;

/**
 * Created by djuelg on 11.07.17.
 */

public interface DeleteHeaderInteractor extends Interactor {
    interface Callback {
        void onHeaderDeleted(TodoListHeader deletedHeader);
    }
}
