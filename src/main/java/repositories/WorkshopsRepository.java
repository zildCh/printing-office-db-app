package repositories;
import entities.Workshops;
import java.util.List;

public class WorkshopsRepository extends BaseRepo<Workshops> {
    public List<Workshops> workshopsList;

    public WorkshopsRepository() {
        workshopsList = super.getAll();
    }

    @Override
    public List<Workshops> getAll() {
        workshopsList = super.getAll();
        return workshopsList;
    }

    @Override
    protected Class<Workshops> getType() {
        return Workshops.class;
    }

    @Override
    protected String getTableName() {
        return "Workshops";
    }
}