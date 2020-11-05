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

    /**
     * The Login.
     */
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    /**
     * The Logout.
     */
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    /**
     * The Registration.
     */
    REGISTRATION {
        {
            this.command = new RegistrationCommand();
        }
    },
    /**
     * The Forward.
     */
    FORWARD {
        {
            this.command = new ForwardCommand();
        }
    },
    /**
     * The Language.
     */
    LANGUAGE {
        {
            this.command = new LanguageCommand();
        }
    },
    /**
     * The Init start page.
     */
    INIT_START_PAGE {
        {
            this.command = new InitStartPageCommand();
        }
    },
    /**
     * The Film.
     */
    FILM {
        {
            this.command = new InitFilmCommand();
        }
    },
    /**
     * The Profile.
     */
    PROFILE {
        {
            this.command = new InitProfileCommand();
        }
    },
    /**
     * The Edit profile.
     */
    EDIT_PROFILE {
        {
            this.command = new EditProfileCommand();
        }
    },
    /**
     * The Forward to edit profile.
     */
    FORWARD_TO_EDIT_PROFILE {
        {
            this.command = new ForwardToEditProfileCommand();
        }
    },
    /**
     * The Init user table.
     */
    ADMIN_PAGE {
        {
            this.command = new InitUserTableCommand();
        }
    },
    /**
     * The View profile.
     */
    VIEW_PROFILE {
        {
            this.command = new ViewProfileCommand();
        }
    },
    /**
     * The Init films table.
     */
    ADMIN_PAGE_FILMS {
        {
            this.command = new InitFilmTableCommand();
        }
    },
    /**
     * The View film page.
     */
    VIEW_FILM_PAGE {
        {
            this.command = new ViewFilmPageCommand();
        }
    },
    /**
     * The Init admin table.
     */
    INIT_ADMIN_TABLE {
        {
            this.command = new InitAdminTableCommand();
        }
    },
    /**
     * The Film creator.
     */
    FILM_CREATOR {
        {
            this.command = new CreateFilmCommand();
        }
    },
    /**
     * The Edit film.
     */
    EDIT_FILM {
        {
            this.command = new EditFilmCommand();
        }
    },
    /**
     * The Forward to edit film.
     */
    FORWARD_TO_EDIT_FILM {
        {
            this.command = new ForwardToEditFilmCommand();
        }
    },
    /**
     * The Purchase film.
     */
    PURCHASE_FILM {
        {
            this.command = new PurchaseFilmCommand();
        }
    },
    /**
     * The View purchased film.
     */
    VIEW_PURCHASED_FILM {
        {
            this.command = new ViewPurchasedFilmCommand();
        }
    },
    /**
     * The Purchased film table.
     */
    PURCHASED_FILM_TABLE {
        {
            this.command = new InitPurchasedFilmTableCommand();
        }
    },
    /**
     * The Search.
     */
    SEARCH {
        {
            this.command = new SearchCommand();
        }
    };

    Command command;

    /**
     * Gets current command.
     *
     * @return the current command
     */
    public Command getCurrentCommand() {
        return command;
    }
}
