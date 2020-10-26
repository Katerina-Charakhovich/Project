package by.epam.project.command;

import by.epam.project.command.impl.*;
import by.epam.project.command.impl.admin.*;
import by.epam.project.command.impl.film.FilmCommand;
import by.epam.project.command.impl.film.InitStartPageCommand;
import by.epam.project.command.impl.user.*;

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
    REGISTRATION {
        {
            this.command = new RegistrationCommand();
        }
    },
    FORWARD {
        {
            this.command = new ForwardCommand();
        }
    },
    LANGUAGE {
        {
            this.command = new LanguageCommand();
        }
    },
    INIT_START_PAGE {
        {
            this.command = new InitStartPageCommand();
        }
    },
    FILM {
        {
            this.command = new FilmCommand();
        }
    },
    PROFILE {
        {
            this.command = new InitProfileCommand();
        }
    },
    EDIT_PROFILE {
        {
            this.command = new EditProfileCommand();
        }
    },
    FORWARD_TO_EDIT_PROFILE {
        {
            this.command = new ForwardToEditProfileCommand();
        }
    },
    ADMIN_PAGE {
        {
            this.command = new InitUserTableCommand();
        }
    },
    VIEW_PROFILE {
        {
            this.command = new ViewProfileCommand();
        }
    },
    ADMIN_PAGE_FILMS {
        {
            this.command = new InitFilmTableCommand();
        }
    },
    VIEW_FILM_PAGE {
        {
            this.command = new ViewFilmPageCommand();
        }
    },
    INIT_ADMIN_TABLE {
        {
            this.command = new InitAdminTableCommand();
        }
    },
    FILM_CREATOR{
        {
            this.command = new FilmCreator();
        }
    };

    Command command;

    public Command getCurrentCommand() {
        return command;
    }
}
