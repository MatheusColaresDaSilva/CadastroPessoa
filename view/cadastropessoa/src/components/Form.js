import { useState, useContext } from "react";
import { AppContext } from "../Context";

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
    <form className="insertForm" onSubmit={submitUser}>
      <h2>Pessoa</h2>
      <label htmlFor="_nome">Nome</label>
      <input
        type="text"
        id="_nome"
        onChange={(e) => addNewUser(e, "nome")}
        placeholder="Nome"
        autoComplete="off"
        required
      />
      <label htmlFor="_cpf">CPF</label>
      <input
        type="text"
        id="_cpf"
        onChange={(e) => addNewUser(e, "cpf")}
        placeholder="CPF"
        autoComplete="off"
        required
        maxLength="11"
      />
      <label htmlFor="_nasc">CPF</label>
      <input
        type="date"
        id="_nasc"
        onChange={(e) => addNewUser(e, "dataNascimento")}
        placeholder="Data Nascimento"
        autoComplete="off"
        required
      />
      <input type="submit" value="Inserir" />
    </form>
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