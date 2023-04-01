import './Calculator.css';
import {useState} from 'react';

import Settings from "./components/Settings";
import Scientific from "./components/Scientific";
import History from "./components/History";

function Calculator() {
    const [histories, setHistories] = useState([]);

    const handleChangeHistory = (display) => {
        const inputSplit = display.value.split('=');
        setHistories(prevState => [...prevState, {label: inputSplit[0], value: inputSplit[1]}]);
    }

    function isValid(str) {
        const errorTextArea = document.getElementById("errorMsg");

        // Make sure we have a closing parentheses for all the open ones
        var openCount = (str.match(/\(/g) || []).length;
        var closeCount = (str.match(/\)/g) || []).length;

        if (closeCount !== openCount) {
            errorTextArea.value = "For each '(' there must be a corresponding ')'";
            return false;
        }

        // fix for preventing functions not starting with '('
        const functions = ["log", "mad", "sd", "sqrt", "arccos", "sinh", "gamma", "^", "abx", "e^", ""]
        for (let i = 0; i < functions.length - 1; i++) {
            // look for each function we are processing
            if (str.includes(functions[i])) {
                // Check for the proper form. Ignoring this condition for certain functions
                if (!(functions[i] === "^" || functions[i] === "e^") && !str.includes(functions[i] + "(")) {
                    errorTextArea.value = "Functions must start with '('";
                    return false
                }
            }
        }

        // Remove everything but the characters for the functions
        const words = str.replace(/[0-9]|,|\.|\+|-|\*|\/|π|\)|\s|\^/g, '').toLowerCase().split('(');

        // Loop through each word and check if it's in the predetermined array
        for (let i = 0; i < words.length; i++) {
            if (!functions.includes(words[i])) {
                // If a word isn't in the array, return false
                errorTextArea.value = "Unknown function: " + words[i];
                return false;
            }
        }

        // If all words are in the array, return true
        errorTextArea.value = '';
        return true;
    }

    function handlePow(input) {
        return input
    }

    function execute() {
        const display = document.getElementById("display");

        if (isValid(display.value)) {
            // Send display.value to the server and append its response
            let request = handlePow(display.value);
            let response = 17;
            display.value += "=" + response;

            handleChangeHistory(display);
            console.log("REQUEST: " + request)
            // prepare the string before sending it to the server
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