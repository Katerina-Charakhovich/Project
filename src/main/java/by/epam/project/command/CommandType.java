package by.epam.project.command;

import by.epam.project.command.impl.LoginCommand;
import by.epam.project.command.impl.LogoutCommand;

public enum CommandType {

    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    };
    Command command;

    public Command getCurrentCommand() {
        return command;
    }


}
