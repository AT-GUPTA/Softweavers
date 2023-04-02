import './History.css';
// import Stack from 'react-bootstrap/Stack';
import Dropdown from 'react-bootstrap/Dropdown';
import Button from 'react-bootstrap/Button';

function History({histories}) {

    const updateDisplay = (event) => {
        const display = document.getElementById("display");
        if (event.target.text !== undefined)
            if (event.target.attributes.value !== undefined)
                display.value = event.target.text + "=" + event.target.attributes.value.value;
            else
                display.value = event.target.text;
        display.focus()
    }

    return (
        <div className="history-container">
            <div className="title">
                History:
            </div>

            <div>
                <Dropdown.Menu show id="history" className="history_input" onChange={(e) => updateDisplay(e)}
                               onClick={(e) => updateDisplay(e)}>
                    {histories.map((history) =>
                        <Dropdown.Item className={"item"} key={histories.indexOf(history)}
                                       value={history.value}>{history.label}</Dropdown.Item>
                    )}
                </Dropdown.Menu>
            </div>
            <div>
                <Button className="save" variant="secondary">Save</Button>
            </div>


        </div>
    );
}

export default History;
