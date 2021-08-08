import { useState, useContext } from "react";
import { AppContext } from "../Context";
const Cadastro = () => {
  const { insertUser } = useContext(AppContext);
  const [newUser, setNewUser] = useState({});

  // Storing the Insert User Form Data.
  const addNewUser = (e, field) => {
    setNewUser({
      ...newUser,
      [field]: e.target.value,
    });
  };

  // Inserting a new user into the Database.
  const submitUser = (e) => {
    
    e.preventDefault();
    insertUser(newUser);
    e.target.reset();
  };

  return (
    <form className="insertForm" onSubmit={submitUser}>
      <h2>Insert Cidade</h2>
      <label htmlFor="_nome">Cidade</label>
      <input
        type="text"
        id="_nome"
        onChange={(e) => addNewUser(e, "nome")}
        placeholder="Nome Cidade"
        autoComplete="off"
        required
      />
      <label htmlFor="_cep">Cep</label>
      <input
        type="text"
        id="_cep"
        onChange={(e) => addNewUser(e, "cep")}
        placeholder="Insira o Cep"
        autoComplete="off"
        required
      />
      <input type="submit" value="Inserir" />
    </form>
  );
};

export default Cadastro;