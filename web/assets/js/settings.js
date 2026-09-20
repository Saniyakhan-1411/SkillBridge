/* ==========================================
   SKILLBRIDGE SETTINGS
   ========================================== */


/* ==========================================
   SETTINGS SECTION NAVIGATION
   ========================================== */

function showSettingsSection(sectionId, clickedButton) {

    const sections =
        document.querySelectorAll(".settings-section");

    sections.forEach(function(section) {

        section.classList.remove("active-section");

    });


    const selectedSection =
        document.getElementById(sectionId);

    if (selectedSection) {

        selectedSection.classList.add(
            "active-section"
        );

    }


    const buttons =
        document.querySelectorAll(".settings-menu");

    buttons.forEach(function(button) {

        button.classList.remove("active");

    });


    if (clickedButton) {

        clickedButton.classList.add("active");

    }

}


/* ==========================================
   THEME SELECTION
   ========================================== */

function selectTheme(theme) {

    if (
        theme !== "light" &&
        theme !== "dark" &&
        theme !== "system"
    ) {
        return;
    }


    /*
     * Save selected theme
     */

    localStorage.setItem(
        "skillbridge-theme",
        theme
    );


    /*
     * Apply immediately
     */

    applyTheme(theme);


    /*
     * Highlight selected option
     */

    updateThemeSelection(theme);

}


/* ==========================================
   APPLY THEME
   ========================================== */

function applyTheme(theme) {

    document.documentElement.setAttribute(
        "data-theme",
        theme
    );

}


/* ==========================================
   UPDATE SELECTED THEME CARD
   ========================================== */

function updateThemeSelection(theme) {

    const options =
        document.querySelectorAll(".theme-option");


    options.forEach(function(option) {

        option.classList.remove("selected");

    });


    const selected =
        document.getElementById(
            theme + "Theme"
        );


    if (selected) {

        selected.classList.add("selected");

    }

}


/* ==========================================
   LOAD SAVED THEME
   ========================================== */

function loadSavedTheme() {

    let savedTheme =
        localStorage.getItem(
            "skillbridge-theme"
        );


    if (
        savedTheme !== "light" &&
        savedTheme !== "dark" &&
        savedTheme !== "system"
    ) {

        savedTheme = "system";

        localStorage.setItem(
            "skillbridge-theme",
            savedTheme
        );

    }


    applyTheme(savedTheme);

    updateThemeSelection(savedTheme);

}


/* ==========================================
   PAGE LOAD
   ========================================== */

document.addEventListener(
    "DOMContentLoaded",
    function() {

        loadSavedTheme();

    }
);