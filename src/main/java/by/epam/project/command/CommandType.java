package by.epam.project.command;

import by.epam.project.command.impl.ForwardCommand;
import by.epam.project.command.impl.LoginCommand;
import by.epam.project.command.impl.LogoutCommand;
import by.epam.project.command.impl.RegistrationCommand;

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
    },
    REGISTRATION{
        {
            this.command = new RegistrationCommand();
        }
    },
    FORWARD{
        {
            this.command = new ForwardCommand();
        }
    };
    Command command;

    public Command getCurrentCommand() {
        return command;
    }


}
