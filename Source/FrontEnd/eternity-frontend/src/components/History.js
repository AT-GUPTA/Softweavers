import './History.css';
import Dropdown from 'react-bootstrap/Dropdown';


function History({histories}) {

    const updateDisplay = (event) => {
        const display = document.getElementById("display");
        if(event.target.text !== undefined)
            display.value = event.target.text + "=" + event.target.attributes.value.value;
        display.focus()
    }

    return (
        <div>
            <div className="history">
                History:
            </div>
            <div>
                <Dropdown.Menu show id="history" className="history_input" onChange={(e) => updateDisplay(e)}
                               onClick={(e) => updateDisplay(e)}>
                    {histories.map((history) =>
                        <Dropdown.Item key={histories.indexOf(history)} value={history.value}>{history.label}</Dropdown.Item>
                    )}
                </Dropdown.Menu>
            </div>
        </div>
    );
}

export default History;
