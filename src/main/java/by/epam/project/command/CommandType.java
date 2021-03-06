package by.epam.project.command;

import by.epam.project.command.impl.*;
import by.epam.project.command.impl.admin.*;
import by.epam.project.command.impl.film.InitFilmCommand;
import by.epam.project.command.impl.film.InitStartPageCommand;
import by.epam.project.command.impl.user.*;

/**
 * The enum Command type.
 */
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
            this.command = new InitFilmCommand();
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
    FILM_CREATOR {
        {
            this.command = new CreateFilmCommand();
        }
    },
    EDIT_FILM {
        {
            this.command = new EditFilmCommand();
        }
    },
    FORWARD_TO_EDIT_FILM {
        {
            this.command = new ForwardToEditFilmCommand();
        }
    },
    PURCHASE_FILM {
        {
            this.command = new PurchaseFilmCommand();
        }
    },
    VIEW_PURCHASED_FILM {
        {
            this.command = new ViewPurchasedFilmCommand();
        }
    },
    PURCHASED_FILM_TABLE {
        {
            this.command = new InitPurchasedFilmTableCommand();
        }
    },
    SEARCH {
        {
            this.command = new SearchCommand();
        }
    };

    Command command;

    public Command getCurrentCommand() {
        return command;
    }
}
