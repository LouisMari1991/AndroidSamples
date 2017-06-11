package com.sync.architecture.blueprints.todoapp.data.source;

import com.sync.architecture.blueprints.todoapp.ApplicationModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * This is a Dagger component. Refer to {@link ToDoApplication} for the list of Dagger components
 * used in this application.
 * <P>
 * Even though Dagger allows annotating a {@link Component @Component} as a singleton, the code
 * itself must ensure only one instance of the class is created. This is done in {@link
 * ToDoApplication}.
 */
@Singleton @Component(modules = { ApplicationModule.class }) public interface TasksRepositoryComponent {

  //TasksRepository getTasksRepository();
}
