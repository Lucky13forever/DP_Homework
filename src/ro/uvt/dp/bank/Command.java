package ro.uvt.dp.bank;

import ro.uvt.dp.exceptions.ClientNotFound;

public interface Command {
    void execute() throws ClientNotFound;
}
