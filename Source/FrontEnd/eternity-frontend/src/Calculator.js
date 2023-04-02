import './Calculator.css';
import {useState} from 'react';

import Settings from "./components/Settings";
import Scientific from "./components/Scientific";
import History from "./components/History";

function Calculator() {
    const [histories, setHistories] = useState([]);

    const handleChangeHistory = (display) => {
        const inputSplit = display.value.split('=');
        if (inputSplit[1]) {
            setHistories(prevState => [...prevState, {label: inputSplit[0], value: inputSplit[1]}]);
        } else {
            setHistories(prevState => [...prevState, {label: inputSplit[0], value: undefined}]);
        }
    }

    /**
     * Checks if the display has a value which conforms to how the server is expecting to
     * receive as input
     * @param display the component containing the input of the user.
     * @returns {boolean|string}
     * When returning a string, the string will contain the error message of where it failed to conform.
     * If true is returned then the display has the correct form for the server.
     * If false is returned then it's because there is already an error message in the display.
     */
    function isValid(display) {
        var str = display.value;
        console.log(str)

        // Remove everything but the characters for the functions
        const words = str.replace(/[0-9]|,|\.|\+|-|\*|\/|\)|\s|\^/g, '').toLowerCase().split('(');

        // Check if there was already an error message in the calculators display
        if (words[0].includes("unknown") || words[0].includes("foreach'") || words[0].includes("functionsmuststartwith"))
            return false;

        // Make sure we have a closing parentheses for all the open ones
        var openCount = (str.match(/\(/g) || []).length;
        var closeCount = (str.match(/\)/g) || []).length;

        if (closeCount !== openCount) {
            return "For each '(' there must be a corresponding ')'";
        }

        // fix for preventing functions not starting with '('
        const functions = ["log", "mad", "sd", "sqrt", "arccos", "sinh", "gamma", "pow", "abx", "pow", ""]
        for (let i = 0; i < functions.length - 1; i++) {
            // look for each function we are processing
            if (str.includes(functions[i])) {
                // Check for the proper form. Ignoring this condition for certain functions
                if (!str.includes(functions[i] + "(")) {
                    return "Functions must start with '('";
                }
            }
        }


        // Loop through each word and check if it's in the predetermined array
        for (let i = 0; i < words.length; i++) {
            // Strip constants
            var constantLess = words[i].replace(/[e|π]/g, '')
            if (!functions.includes(constantLess)) {
                // If a word isn't in the array, return false
                return "Unknown function: " + words[i];
            }
        }

        // If all words are in the array, return true
        return true;
    }

    function execute() {
        const display = document.getElementById("display");
        let request = display.value;

        if (request !== '') {
            var validated = isValid(display);
            if (validated === true) {
                // Send request to the server and append its response
                let response = 17;
                display.value += "=" + response;
                handleChangeHistory(display);
            }
            if (typeof (validated) === "string") {
                handleChangeHistory(display);
                display.value = validated;
            }
        }
    }

    return (
        <div>
            <div className="title-bar">
                <h1>Eternity</h1>
                <h2>The Scientific Calculator</h2>
            </div>
            <div className="columns">
                <div className="first-column">
                    <Scientific execute={execute}/>
                </div>
                <div className="second-column">
                    <Settings/>
                    <History histories={histories}/>
                </div>
            </div>
        </div>
    );
}

export default Calculator;