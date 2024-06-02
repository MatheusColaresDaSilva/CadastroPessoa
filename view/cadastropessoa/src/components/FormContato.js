import { useState, useContext } from "react";
import { AppContext } from "../Context";
import TodoList from "./TodoList"

const Form = () => {
  const { insertUser } = useContext(AppContext);
  const [newUser, setNewUser] = useState({});

  const addNewUser = (e, field) => {
    setNewUser({
      ...newUser,
      [field]: e.target.value,
    });
  };

  const submitUser = (e) => {
    e.preventDefault();
    insertUser(newUser);
    e.target.reset();
  };

  return (
    <div>
        <TodoList/>
    </div>
  );
};

export default Form;

/*
<label htmlFor="_cep">Email</label>
      <input
        type="text"
        id="_cep"
        onChange={(e) => addNewUser(e, "cep")}
        placeholder="Email"
        autoComplete="off"
        required
      />
      */