import React, { Component } from "react";

export default class MenuList extends Component {
  render() {
    return (
      <React.Fragment>
        <div className="row justify-content-center">
          <h1>{this.props.listHeader}</h1>
        </div>
        <div className="row justify-content-center">
          <ul className="list-group mt-3 w-75">{this.props.children}</ul>
        </div>
      </React.Fragment>
    );
  }
}
