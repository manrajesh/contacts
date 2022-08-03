import React, { Component } from "react";
import ReactDOM from "react-dom/client";
import '../css/main.css';
import Contacts from './componets/Contacts';

class Main extends Component {
    render() {
        return (
            <div>
                <Contacts />
            </div>
        );
    }
}

const root = ReactDOM.createRoot(document.getElementById("react-mountpoint"));
root.render(
  <React.StrictMode>
    <Main />
  </React.StrictMode>
);
