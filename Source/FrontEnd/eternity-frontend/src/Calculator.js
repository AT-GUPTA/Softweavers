
import './Calculator.css';

// Get the display element
const display = document.getElementById("display");

// Get all the button elements
const buttons = document.querySelectorAll("s#keys button");

// Loop through each button element
buttons.forEach((button) => {
  // Add a click event listener
  button.addEventListener("click", () => {
    // Append the button's value to the display
    display.value += button.textContent;
  });
});

function Calculator() {
  return (
      <div>
        <div className="title-bar">
          Scientific Calculator
        </div>
        <div className="columns">
          <div className="first-column">
            <div id="calculator">
              <input type="text" id="display" readOnly/>
              <div id="keys">
                <button className="function" id="sin">sinh</button>
                <button className="function" id="arcos">arcos</button>
                <button className="function" id="mad">mad</button>
                <button className="function" id="gamma">gamma</button>
                <button className="function" id="log">log</button>
                <button className="function" id="sqrt">sqrt</button>
                <button className="function" id="inv">1/x</button>
                <button className="function" id="pow">x^y</button>
                <button className="operator">+</button>
                <button className="operator">-</button>
                <button className="operator">*</button>
                <button className="operator">/</button>
                <button>7</button>
                <button>8</button>
                <button>9</button>
                <button>4</button>
                <button>5</button>
                <button>6</button>
                <button>1</button>
                <button>2</button>
                <button>3</button>
                <button>0</button>
                <button id="decimal">.</button>
                <button id="clear">C</button>
                <button id="euler">e</button>
                <button id="pi">π</button>
                <button id="exp">e^x</button>
                <button id="equals">=</button>
              </div>
            </div>
          </div>

          <div className="second-column">
            <div className="header">
              Settings
            </div>
            <div>
              <label>
                <input type="checkbox" className="switch"/>
                On/Off
              </label>
            </div>
            <div>
              <select className="dropdown">
                <option value="option1">Option 1</option>
                <option value="option2">Option 2</option>
                <option value="option3">Option 3</option>
              </select>
            </div>

            <div className="history">
              History
            </div>
            <div>
            </div>
          </div>
        </div>
      </div>
  );
}

export default Calculator;
