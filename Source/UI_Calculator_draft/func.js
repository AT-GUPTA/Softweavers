// Get the display element
const display = document.getElementById("display");

// Get all the button elements
const buttons = document.querySelectorAll("#keys button");

// Loop through each button element
buttons.forEach((button) => {
    // Add a click event listener
    button.addEventListener("click", () => {
        // Append the button's value to the display
        display.value += button.textContent;
    });
});