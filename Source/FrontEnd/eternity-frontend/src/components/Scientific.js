import { useCallback } from 'react';
import './Scientific.css';
import {useEffect, useRef} from 'react';
import Tooltip from './Tooltip';

function Scientific({execute}) {
  const displayRef = useRef(null);

  const onClickFunction = useCallback((button) => {
    const display = displayRef.current;

    // Append the button's value to the display
    let cursorStartPosition = display.selectionStart;
    let cursorEndPosition = display.selectionEnd;

    // Handle when we want to insert in a particular location
    if ((cursorEndPosition - cursorStartPosition) === 0) {
      let textBeforeCursorPosition = display.value.substring(0, cursorStartPosition)
      let textAfterCursorPosition = display.value.substring(cursorStartPosition, display.value.length)
      display.value = textBeforeCursorPosition + button.value + textAfterCursorPosition;
    }
    // Handle when we select a part of the display to replace with something else
    else {
      let toReplace = display.value.substring(cursorStartPosition, cursorEndPosition);
      display.value = display.value.replace(toReplace, button.value);
    }

    // Handle leaving the cursor in the correct location for inserting variable
    if (button.id === "abx")
      display.selectionEnd = display.selectionStart - 4;

    display.focus();
  }, []);


  const onOtherButtonClick = useCallback ((button) => {
    const display = displayRef.current;

    // Append the button's value to the display
    display.value += button.textContent;
    display.focus();
  }, [])

  useEffect(() => {
    const function_buttons = document.querySelectorAll(".function");
    function_buttons.forEach(function (button) {
      button.addEventListener("click", () => onClickFunction(button));
    });

    const other_buttons = document.querySelectorAll(".constant, .punctuation, .operator, .number");
    other_buttons.forEach(function (button) {
      button.addEventListener("click", () => onOtherButtonClick(button));
    });

    const keyDownHandler = event => {
      if (event.key === 'Enter') {
        event.preventDefault();
        execute();
      }
    };

    document.addEventListener('keydown', keyDownHandler);
    return () => {
      function_buttons.forEach(function (button) {
        button.removeEventListener("click", () => onClickFunction);
      });
      other_buttons.forEach(function (button) {
        button.removeEventListener("click", () => onOtherButtonClick);
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
  const buttonTooltips = Tooltip();

  const generateTooltip = (buttonId) => {
    const tooltip = buttonTooltips[buttonId];
    return `
    ${tooltip.head}\n\n${tooltip.description}\n\nInput Arguments:\n${tooltip.inputParams}\n\nReturns:\n${tooltip.returns}\n\nExceptions:\n${tooltip.exceptions}
    `;
};

  return (
    <div id="calculator">
      <label htmlFor="display"></label><textarea ref={displayRef} id="display" onChange={submitPrep} autoFocus></textarea>
      <div id="keys">
                <button className="function" id="sinh" value="sinh(" title={generateTooltip("sinh")}>sinh</button>
                <button className="function" id="arccos" value="arccos(" title={generateTooltip("arccos")}>cos<sup>-1</sup></button>
                <button className="function" id="mad" value="mad(" title='Mean Absolute Deviation Function*'>mad</button>
                <button className="function" id="gamma" value="gamma(" title={generateTooltip("gamma")}>gamma</button>
                <button className="function" id="log" value="log(" title={generateTooltip("log")}>log</button>
                <button className="function" id="sqrt" value="pow(, 1/2)" title={generateTooltip("sqrt")}>sqrt</button>
                <button className="function" id="pow" value="pow(" title={generateTooltip("pow")}>x<sup>y</sup></button>
                <button className="function" id="sd" value="sd(" title={generateTooltip("sd")}>sd</button>
                <button className="function" id="abx" value="abx(, ,)" title={generateTooltip("abx")}>ab<sup>x</sup></button>
                <button className="function" id="exp" value="pow(e,)" title={generateTooltip("exp")}>e<sup>x</sup></button>
                <button className="constant" id="euler" title="Euler\'s constant">e</button>
                <button className="constant" id="pi" title="Math Constant pi">π</button>
                <button className="number" title="Number 7">7</button>
                <button className="number" title="Number 8">8</button>
                <button className="number" title="Number 9">9</button>
                <button className="operator" title="Addition Operator">+</button>
                <button className="number" title="Number 4">4</button>
                <button className="number" title="Number 5">5</button>
                <button className="number" title="Number 6">6</button>
                <button className="operator"  title="Subtraction Operator">-</button>
                <button className="number" title="Number 1">1</button>
                <button className="number" title="Number 2">2</button>
                <button className="number" title="Number 3">3</button>
                <button className="operator" title="Multiplication Operator">*</button>
                <button className="number" title="Number 0">0</button>
                <button className="punctuation" id="decimal" title="Decimal Point">.</button>
                <button id="clear" onClick={clearDisplay} title="Clear Input">C</button>
                <button className="operator" title="Division Operator">/</button>
                <button className="punctuation" id="open" title="Right Parenthesis">(</button>
                <button className="punctuation" id="comma" title="Comma">,</button>
                <button className="punctuation" id="close"  title="Left Parenthesis">)</button>
                <button id="equals" onClick={execute} title="Calculate Rresult">=</button>
            </div>
        </div>
    );
}

export default Scientific;
