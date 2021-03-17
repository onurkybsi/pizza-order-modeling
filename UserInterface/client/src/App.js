import React, { Component } from "react";
import MenuList from "./components/MenuList";
import MenuItem from "./components/MenuItem";
import configuration from "./configuration.json";
import axios from "axios";
import PaymentModal from "./components/PaymentModal";

export default class App extends Component {
  getMenuURL = `${configuration.ModelProducerBaseURL}${configuration.ModelProducerGetMenuEndPoint}`;
  menu = [];

  constructor(props) {
    super(props);

    this.state = {
      loading: true,
      showPaymentModal: false,
      orderedMenuItems: {},
      totalAmount: 0,
    };
  }

  componentDidMount() {
    axios
      .get(this.getMenuURL)
      .then((response) => {
        this.menu = response.data;
        this.setState({ loading: false });
      })
      .catch(function (error) {
        // handle error
        alert(error);
      });
  }

  setMenuItemInfoToOrder = (menuItem) => {
    if (menuItem === undefined || menuItem === null) return;

    this.setState((state) => ({
      totalAmount: this.calculateNewTotalAmountByOrderChange(menuItem),
      orderedMenuItems: {
        ...state.orderedMenuItems,
        [menuItem.id]: menuItem.quantity,
      },
    }));
  };

  calculateNewTotalAmountByOrderChange = (changedMenuItem) => {
    console.log("changedMenuItem:", changedMenuItem);
    if (
      this.state.orderedMenuItems[changedMenuItem.id] > changedMenuItem.quantity
    ) {
      return (
        this.state.totalAmount -
        (this.state.orderedMenuItems[changedMenuItem.id] -
          changedMenuItem.quantity) *
          changedMenuItem.price
      );
    } else {
      return (
        this.state.totalAmount +
        (changedMenuItem.quantity -
          (this.state.orderedMenuItems[changedMenuItem.id] === undefined
            ? 0
            : this.state.orderedMenuItems[changedMenuItem.id])) *
          changedMenuItem.price
      );
    }
  };

  getOrderedMenuItems = () => {
    let orderedMenuItems = [];
    for (const [key, value] of Object.entries(this.state.orderedMenuItems)) {
      if (value === 0) return;
      orderedMenuItems.push({ id: key, quantity: value });
    }

    return orderedMenuItems;
  };

  render() {
    if (this.state.loading) {
      return (
        <>
          <div class="row justify-content-center mt-5">
            <h1>Welcome to PizzaStore !</h1>
          </div>
          <div class="row justify-content-center mt-5">
            <div className="sp sp-circle"></div>
          </div>
        </>
      );
    } else {
      return (
        <React.Fragment>
          <div className="container-fluid">
            <div className="row mt-3">
              <div className="col-6 col-md-6 col-sm-12">
                <MenuList listHeader="Pizzas">
                  {this.menu.map((item) => {
                    if (item.type !== "PIZZA") return null;
                    return (
                      <MenuItem
                        key={item.id}
                        item={{
                          id: item.id,
                          name: item.name,
                          requiredMaterials: item.requiredMaterials,
                          price: item.price,
                        }}
                        setItemInfoToOrder={this.setMenuItemInfoToOrder}
                      />
                    );
                  })}
                </MenuList>
              </div>
              <div className="col-6 col-md-6 col-sm-12">
                <MenuList listHeader="Drinks">
                  {this.menu.map((item) => {
                    if (item.type !== "DRINK") return null;
                    return (
                      <MenuItem
                        key={item.id}
                        item={{
                          id: item.id,
                          name: item.name,
                          requiredMaterials: item.requiredMaterials,
                          price: item.price,
                        }}
                        setItemInfoToOrder={this.setMenuItemInfoToOrder}
                      />
                    );
                  })}
                </MenuList>
              </div>
            </div>
          </div>
          <div className="row mt-3 d-flex flex-column align-items-center justify-content-center mt-5">
            <div className="col-12 d-flex  align-items-center justify-content-center mb-3">
              <h4 className="mr-2">Total amount: </h4>{" "}
              <h3>{this.state.totalAmount}.00$</h3>
            </div>
            <button
              type="button"
              className="btn btn-success btn-lg"
              onClick={() => this.setState({ showPaymentModal: true })}
            >
              Make Order
            </button>
            <PaymentModal
              showPaymentModal={this.state.showPaymentModal}
              closeModal={() => this.setState({ showPaymentModal: false })}
              getOrderedMenuItems={this.getOrderedMenuItems}
            />
          </div>
        </React.Fragment>
      );
    }
  }
}
