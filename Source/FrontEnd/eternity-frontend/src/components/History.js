import './History.css';
import Dropdown from 'react-bootstrap/Dropdown';


function History({histories}) {

    const updateDisplay = (event) => {
        const display = document.getElementById("display");
        if(event.target.text !== undefined)
            display.value = event.target.text;
        display.focus()
    }

    return (
        <div>
            <div className="history">
                History:
            </div>
            <div>
                <Dropdown.Menu show className="history_input" onChange={(e) => updateDisplay(e)}
                               onClick={(e) => updateDisplay(e)}>
                    {histories.map((history) =>
                        <Dropdown.Item eventKey={history.eventKey}>{history.label}</Dropdown.Item>
                    )}
                </Dropdown.Menu>
            </div>
        </div>
    );
}

export default History;
