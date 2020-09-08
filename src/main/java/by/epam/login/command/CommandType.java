package by.epam.login.command;

import by.epam.login.command.impl.LoginCommand;
import by.epam.login.command.impl.LogoutCommand;

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
