import './Scientific.css';
import {useEffect, useRef} from 'react';
import Tooltip from './Tooltip';

function Scientific({execute}) {
  const displayRef = useRef(null);

  const onOtherButtonClick = (button) => {
    const display = displayRef.current;
    const index = display.selectionEnd;

    const currentValueBeforeIndex = display.value.slice(0, index);
    const currentValueAfterIndex = display.value.slice(index);

    // Set the new value of the textarea with the button's value inserted at the index
    display.value = `${currentValueBeforeIndex}${button.textContent}${currentValueAfterIndex}`;

    // Set the selection end index to the end of the inserted text
    display.selectionEnd = index + button.textContent.length;

    // If the cursor is inside a function and preceding a comma, sets the cursor to after the comma.
    if (display.value[index + 1] === ",") {
      display.selectionEnd++;
      display.selectionStart++;
    }
    display.focus();
  };

  const handleFunctionClick = (event) => {
    const display = displayRef.current;
    const buttonValue = event.target.value;
  
    // Append the button's value to the display
    let cursorStartPosition = display.selectionStart;
    let cursorEndPosition = display.selectionEnd;
  
    // Gets the index of the | character, which indicates the cursor position
    let cursorPosition = buttonValue.indexOf("|");
  
    // Handle when we want to insert in a particular location
    if ((cursorEndPosition - cursorStartPosition) === 0) {
      const textBeforeCursorPosition = display.value.slice(0, cursorStartPosition);
      const textAfterCursorPosition = display.value.slice(cursorStartPosition);

      console.log(textBeforeCursorPosition, textAfterCursorPosition);
  
      if (textBeforeCursorPosition.length > 0) {
        cursorPosition += textBeforeCursorPosition.length;
      }
  
      display.value =
        textBeforeCursorPosition +
        buttonValue.replace("|", "") +
        textAfterCursorPosition;
    }
    // Handle when we select a part of the display to replace with something else
    else {
      let toReplace = display.value.substring(cursorStartPosition, cursorEndPosition);
      display.value = display.value.replace(toReplace, buttonValue);
    }
  
    // Places the cursor at the right position for the current function
    display.selectionEnd = cursorPosition;
  
    display.focus();
  };
  

  useEffect(() => {
    const other_buttons = document.querySelectorAll(".constant, .punctuation, .operator, .number");
    other_buttons.forEach(function (button) {
      button.addEventListener("click", onOtherButtonClick.bind(this, button));
    });
  
    const keyDownHandler = event => {
      if (event.key === 'Enter') {
        event.preventDefault();
        execute();
      }
    };
  
    document.addEventListener('keydown', keyDownHandler);
    return () => {
      other_buttons.forEach(function (button) {
        button.removeEventListener("click", onOtherButtonClick.bind(this, button));
      });
      document.removeEventListener('keydown', keyDownHandler);
    };
  }, []);

  function clearDisplay() {
    const display = displayRef.current;
    display.value = "";
    display.focus();
  }

  function submitPrep() {
    const display = displayRef.current;
    if (display.value.includes('=')) {
      display.value = display.value.split('=')[0];
    }
  }

  /**
   * Performs a backspace, which removes the last added character from the display.
   */
  function performBackspace() {
    const display = displayRef.current;

    // Removing the last value of the display
    display.value = display.value.slice(0, display.value.length - 1);
    display.focus();
  }

  const buttonTooltips = Tooltip();

  const generateTooltip = (buttonId) => {
    const tooltip = buttonTooltips[buttonId];
    return `
    ${tooltip.head}\n\n${tooltip.description}\n\nInput Arguments:\n${tooltip.inputParams}\n\nReturns:\n${tooltip.returns}\n\nExceptions:\n${tooltip.exceptions}
    `;
};

  return (
    <div id="calculator">
      <label htmlFor="display"></label>
      <textarea
        ref={displayRef}
        id="display"
        onChange={submitPrep}
        autoFocus
      ></textarea>
      <div id="keys">
                <button className="function" id="sinh" value="sinh(|)" title={generateTooltip("sinh")} onClick={handleFunctionClick}>sinh</button>
                <button className="function" id="arccos" value="arccos(|)" title={generateTooltip("arccos")} onClick={handleFunctionClick}>cos<sup>-1</sup></button>
                <button className="function" id="gamma" value="gamma(|)" title={generateTooltip("gamma")} onClick={handleFunctionClick}>gamma</button>
                <button id="clear" onClick={clearDisplay} title="Clear Input">C</button>
                <button className="function" id="log" value="log(|,)" title={generateTooltip("log")} onClick={handleFunctionClick}>log</button>
                <button className="function" id="sqrt" value="pow(|,  1/2)" title={generateTooltip("sqrt")} onClick={handleFunctionClick}>sqrt</button>
                <button className="function" id="pow" value="pow(|)" title={generateTooltip("pow")} onClick={handleFunctionClick}>x<sup>y</sup></button>
                <button id="backspace" onClick={performBackspace} title="Backspace">&#9003;</button>
                <button className="function" id="sd" value="sd(|)" title={generateTooltip("sd")}>sd</button>
                <button className="function" id="abx" value="abx(|,,)" title={generateTooltip("abx")} onClick={handleFunctionClick}>ab<sup>x</sup></button>
                <button className="function" id="exp" value="pow(e,|)" title={generateTooltip("exp")} onClick={handleFunctionClick}>e<sup>x</sup></button>
                <button className="punctuation" id="comma" title="Comma">,</button>
                <button className="number" title="Number 7">7</button>
                <button className="number" title="Number 8">8</button>
                <button className="number" title="Number 9">9</button>
                <button className="operator" title="Addition Operator">+</button>
                <button className="number" title="Number 4">4</button>
                <button className="number" title="Number 5">5</button>
                <button className="number" title="Number 6">6</button>
                <button className="operator" title="Subtraction Operator">-</button>
                <button className="number" title="Number 1">1</button>
                <button className="number" title="Number 2">2</button>
                <button className="number" title="Number 3">3</button>
                <button className="operator" title="Multiplication Operator">*</button>
                <button className="punctuation" id="open" title="Right Parenthesis">(</button>
                <button className="number" title="Number 0">0</button>
                <button className="punctuation" id="close"  title="Left Parenthesis">)</button>
                <button className="operator" title="Division Operator">/</button>
                <button className="constant" id="pi" title="Math Constant pi">π</button>
                <button className="constant" id="euler" title="Euler\'s constant">e</button>
                <button className="punctuation" id="decimal" title="Decimal Point">.</button>
                <button id="equals" onClick={execute} title="Calculate Result">=</button>
            </div>
        </div>
    );
}

export default Scientific;
