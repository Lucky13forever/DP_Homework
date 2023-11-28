package ro.uvt.dp.commander;

import ro.uvt.dp.exceptions.ClientNotFound;

public interface Command {
    void execute() throws ClientNotFound;
}
