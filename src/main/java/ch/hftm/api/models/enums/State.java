package ch.hftm.api.models.enums;

/**
 * Used to determine, which state of the window should be manipulated.
 * Desired is what the window should be.
 * Current is what the window actualy is.
 * The window controller will always try to make sure, that the current state
 * matches the desired state.
 */
public enum State {

    DESIRED {
    },
    CURRENT {
    };
}
