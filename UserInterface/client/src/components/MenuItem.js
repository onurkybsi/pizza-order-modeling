import React, { Component } from "react";

export default class MenuItem extends Component {
  constructor(props) {
    super(props);

    this.state = {
      quantity: 0,
    };
  }
  handleQuantityChange = (e) => {
    if (e.target.id === "decrease" && this.state.quantity === 0) return;

    this.setState(
      {
        quantity:
          e.target.id === "increase"
            ? this.state.quantity + 1
            : this.state.quantity - 1,
      },
      () =>
        this.props.setItemInfoToOrder({
          id: this.props.item.id,
          quantity: this.state.quantity,
        })
    );
  };

  render() {
    let withMaterials = this.props.item.requiredMaterials.length > 0;
    let materialsText = "";
    this.props.item.requiredMaterials.forEach((material) => {
      if (materialsText === "") {
        materialsText = material.name;
      } else {
        materialsText = `${materialsText}, ${material.name}`;
      }
    });

    return (
      <li className="list-group-item d-flex justify-content-between align-items-center">
        <div className="description">
          <h5>{this.props.item.name}</h5>
          {withMaterials && <small>{materialsText}</small>}
        </div>
        <h4 style={{ textAlign: "center" }}>{this.props.item.price} $</h4>
        <div className="button-control d-flex mr-2" style={{ width: "100px" }}>
          <div className="input-group-prepend">
            <button
              id="decrease"
              className="btn btn-danger"
              onClick={this.handleQuantityChange}
            >
              -
            </button>
          </div>
          <div className="col-2">
            <h4 style={{ textAlign: "center" }}>{this.state.quantity}</h4>
          </div>
          <div className="input-group-append">
            <button
              id="increase"
              className="btn btn-success"
              onClick={this.handleQuantityChange}
            >
              +
            </button>
          </div>
        </div>
      </li>
    );
  }
}
