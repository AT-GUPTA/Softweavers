import './Settings.css';


function Settings({onPrecisionChange}) {
    function createSelectItems() {
        let items = [];
        for (let i = 1; i <= 12; i++) {
            items.push(<option key={i} value={i}>{i}</option>);
        }
        return items;
    }

    return (
      <div>
        <div className="header">Settings:</div>
        <div>
          <label>
            <input type="checkbox" className="switch" />
            &nbsp;&nbsp;Scientific notation
          </label>
        </div>
        <div>
          <div className="header"> Decimal Precision:</div>
          <label className="flushOption">
            Precision Value:
            <select
              className="dropdown"
              onChange={onPrecisionChange}
              defaultValue={5}
            >
              {createSelectItems().map((precision) => precision)}
            </select>
            &nbsp;decimal places.
          </label>
        </div>
      </div>
    );
}

export default Settings;
