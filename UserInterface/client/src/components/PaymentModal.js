import React, { Component } from "react";
import { Modal, Button } from "react-bootstrap";
import configuration from "../configuration.json";
import { create as createAxiosInstance } from "axios";

const axiosInstance = createAxiosInstance({
  baseURL: configuration.ModelProducerBaseURL,
  timeout: 5000,
});

export default class PaymentModal extends Component {
  constructor(props) {
    super(props);
    this.state = {
      waitForPayment: false,
      creditCard: {
        nameOnCard: "",
        cardNumber: "",
        expirationMonth: "1",
        expirationYear: "2022",
        cvv: "",
      },
      showPaymentMessage: false,
      paymentMessageIsError: false,
      paymentMessage: "",
      paymentCompleted: false,
    };
  }

  handleClose = () => {
    this.clearState();
    this.props.closeModal();
  };

  clearState = () => {
    this.setState((state) => ({
      waitForPayment: false,
      creditCard: {
        nameOnCard: "",
        cardNumber: "",
        expirationMonth: "1",
        expirationYear: "2022",
      },
      showPaymentMessage: false,
      paymentMessageIsError: false,
      paymentMessage: "",
      paymentCompleted: false,
    }));
  };

  handleConfirmPayment = async () => {
    this.setState({ waitForPayment: true });

    let formDataValidation = this.validateFormData();
    if (!formDataValidation.isValid) {
      this.handleUnsuccessfulPayment(formDataValidation.message);
    } else {
      let createOrderRequest = this.getCreateOrderRequest();
      let createOrderResponse = await this.sendCreateOrderRequest(
        createOrderRequest
      );
      if (this.checkResponseIsSuccess(createOrderResponse)) {
        this.handleSuccessfulPayment(configuration.SuccessfulOrderMessage);
      } else {
        this.handleUnsuccessfulPayment(configuration.ErrorMessage);
      }
    }
    this.setState({ waitForPayment: false });
  };

  validateFormData = () => {
    let validationResult = { isValid: true, message: "" };
    if (this.state.creditCard.nameOnCard === "") {
      validationResult.isValid = false;
      validationResult.message = configuration.INVALID_CARD_OWNER_NAME_MESSAGE;
      return validationResult;
    }
    if (
      this.state.creditCard.cardNumber === "" ||
      this.state.creditCard.cardNumber.length < 16
    ) {
      validationResult.isValid = false;
      validationResult.message = configuration.INVALID_CARD_NUMBER_MESSAGE;
      return validationResult;
    }
    if (
      this.state.creditCard.cvv === "" ||
      this.state.creditCard.cvv.length < 3
    ) {
      validationResult.isValid = false;
      validationResult.message = configuration.INVALID_CVV_MESSAGE;
      return validationResult;
    }
    return validationResult;
  };

  handleUnsuccessfulPayment = (errorMessage) => {
    this.setState(
      (state) => ({
        waitForPayment: false,
        showPaymentMessage: true,
        paymentMessageIsError: true,
        paymentMessage: errorMessage,
      }),
      () => setTimeout(() => this.clearPaymentMessage(), 3000)
    );
  };

  handleSuccessfulPayment = (message) => {
    this.setState((state) => ({
      waitForPayment: false,
      showPaymentMessage: true,
      paymentMessageIsError: false,
      paymentMessage: message,
      paymentCompleted: true,
    }));
  };

  clearPaymentMessage = () => {
    this.setState({
      showPaymentMessage: false,
      paymentMessageIsError: false,
      paymentMessage: "",
    });
  };

  getCreateOrderRequest = () => {
    return {
      creditCardInfo: {
        nameOnCard: this.state.creditCard.nameOnCard,
        cardNumber: this.state.creditCard.cardNumber,
        expirationMonth: Number(this.state.creditCard.expirationMonth),
        expirationYear: Number(this.state.creditCard.expirationYear),
        cvv: Number(this.state.creditCard.cvv),
      },
      orderedMenuItems: this.props.getOrderedMenuItems(),
    };
  };

  sendCreateOrderRequest = async (createOrderRequest) => {
    let createOrderResponse = await axiosInstance.post(
      configuration.ModelProducerCreateOrderEndPoint,
      createOrderRequest
    );
    return createOrderResponse;
  };

  checkResponseIsSuccess = (createOrderResponse) => {
    return (
      (createOrderResponse.status >= 200 || createOrderResponse.status < 300) &&
      createOrderResponse.data.isSuccess
    );
  };

  handleInputChange = (e, onlyNumber = false, validateValue = undefined) => {
    if (e.target.value === null || e.target.value.match(/^ *$/) !== null)
      return;
    if (onlyNumber && e.target.value !== "" && isNaN(e.target.value)) return;
    if (validateValue !== undefined && !validateValue()) return;

    this.setState(
      (state) => ({
        creditCard: {
          ...state.creditCard,
          [e.target.id]: e.target.value.trim(),
        },
      }),
      () => console.log(this.state.creditCard)
    );
  };

  getRange = (lowerBoud, upperBoud) => {
    let range = [];
    let i = lowerBoud;
    while (i < upperBoud) {
      range.push(i);
      i++;
    }
    return range;
  };

  render() {
    return (
      <Modal show={this.props.showPaymentModal}>
        <Modal.Header
          closeButton={this.state.paymentCompleted}
          onHide={this.handleClose}
        >
          <Modal.Title>Payment</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <form>
            <div className="form-group">
              <label>Cart owner name</label>
              <input
                id="nameOnCard"
                className="form-control"
                placeholder="Onur Kayabasi"
                onChange={this.handleInputChange}
              />
            </div>
            <div className="form-group">
              <label>Cart Number</label>
              <input
                id="cardNumber"
                className="form-control"
                placeholder="0000 0000 0000 0000"
                value={this.state.creditCard.cardNumber}
                onChange={(e) =>
                  this.handleInputChange(
                    e,
                    true,
                    () => e.target.value.length <= 16
                  )
                }
              />
            </div>
            <div className="form-group">
              <label>Expiration Month</label>
              <select
                id="expirationMonth"
                className="form-control"
                onChange={(e) => this.handleInputChange(e)}
              >
                {this.getRange(1, 13).map((value) => {
                  return <option key={value}>{value}</option>;
                })}
              </select>
            </div>
            <div className="form-group">
              <label>Expiration Year</label>
              <select
                id="expirationYear"
                className="form-control"
                onChange={(e) => this.handleInputChange(e)}
              >
                {this.getRange(2022, 2030).map((value) => {
                  return <option key={value}>{value}</option>;
                })}
              </select>
            </div>
            <div className="form-group">
              <label>CVV</label>
              <input
                id="cvv"
                className="form-control"
                placeholder="123"
                value={this.state.creditCard.cvv}
                onChange={(e) =>
                  this.handleInputChange(
                    e,
                    true,
                    () => e.target.value.length <= 3
                  )
                }
              />
            </div>
          </form>
        </Modal.Body>
        <Modal.Footer style={{ height: "5em" }}>
          {this.state.showPaymentMessage && (
            <div
              className={`alert alert-${
                this.state.paymentMessageIsError ? "danger" : "success"
              }`}
              role="alert"
            >
              {this.state.paymentMessage}
            </div>
          )}
          {!this.state.showPaymentMessage && !this.state.waitForPayment && (
            <>
              {" "}
              <Button variant="secondary" onClick={this.handleClose}>
                Close
              </Button>
              <Button variant="primary" onClick={this.handleConfirmPayment}>
                Confirm Payment
              </Button>{" "}
            </>
          )}
          {!this.state.showPaymentMessage && this.state.waitForPayment && (
            <div className="col-sm-2" style={{ marginTop: "-20px" }}>
              <div className="sp sp-circle"></div>
            </div>
          )}
        </Modal.Footer>
      </Modal>
    );
  }
}
