import { useContext, useState } from "react";
import { AppContext } from "../Context";

const PessoaList = () => {
  const {
    users,
    userLength
  } = useContext(AppContext);

  const [newData, setNewData] = useState({});

  return !userLength ? (
    <p>{userLength === null ? "Carregando..." : "Por favor, insira alguma pessoa."}</p>
  ) : (
    <table>
      <thead>
        <tr>
          <th>Nome</th>
          <th>CPF</th>
        </tr>
      </thead>
      <tbody>
        {users.map(function (element) {
                     return <tr key={element["id"]}>
                     <td>{element["nome"]}</td>
                     <td>{element["cpf"]}</td>
                   </tr>;
                  })}
      </tbody>
    </table>
  );

};

export default PessoaList;