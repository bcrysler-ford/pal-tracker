package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private List<TimeEntry> storedEntries;
    private long currentID;

    public InMemoryTimeEntryRepository() {
        this.storedEntries = new ArrayList<>();
        this.currentID = 1L;
    }

    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(currentID);
        storedEntries.add(timeEntry);
        currentID++;
        return timeEntry;
    }

    public TimeEntry find(long timeEntryId) {
        for (TimeEntry entry : storedEntries) {
            if (entry.getId() == timeEntryId)
                return entry;
        }

        return null;
    }

    public List<TimeEntry> list() {
        return storedEntries;
    }

    public TimeEntry update(long eq, TimeEntry desiredState) {

        for (TimeEntry entry : storedEntries) {
            if (entry.getId() == eq) {
                entry.setProjectId(desiredState.getProjectId());
                entry.setUserId(desiredState.getUserId());
                entry.setDate(desiredState.getDate());
                entry.setHours(desiredState.getHours());
                return entry;
            }
        }

        return null;
    }

    public void delete(long timeEntryId) {

        TimeEntry toDelete = null;

        for (TimeEntry entry : storedEntries) {
            if (entry.getId() == timeEntryId)
                toDelete = entry;
        }

        if (null != toDelete)
            storedEntries.remove(toDelete);
    }
}
