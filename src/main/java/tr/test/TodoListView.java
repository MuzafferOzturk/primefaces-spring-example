package tr.test;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class TodoListView {
    @Autowired
    private TodoService todoService;
    private DualListModel<Todo> todos;

    public TodoListView() {

    }

    @PostConstruct
    public void init() {
        List<Todo> todosSource = todoService.getTodos().subList(0, 5);
        List<Todo> todosTarget = new ArrayList<>();

        todos = new DualListModel<>(todosSource, todosTarget);

    }

    public DualListModel<Todo> getTodos() {
        return todos;
    }

    public void setTodos(DualListModel<Todo> todos) {
        this.todos = todos;
    }

    public TodoService getTodoService() {
        return todoService;
    }

    public void setTodoService(TodoService todoService) {
        this.todoService = todoService;
    }

    public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for(Object item : event.getItems()) {
            builder.append(((Todo) item).getTitle()).append("<br />");
        }

        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Items Transferred");
        msg.setDetail(builder.toString());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onSelect(SelectEvent<Todo> event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().getTitle()));
    }

    public void onUnselect(UnselectEvent<Todo> event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().getTitle()));
    }

    public void onReorder() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    }
}
