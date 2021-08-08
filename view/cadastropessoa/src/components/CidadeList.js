import { useContext, useState } from "react";
import { AppContext } from "../Context";

const CidadeList = () => {
  const {
    users,
    userLength
  } = useContext(AppContext);

  // Storing users new data when they editing their info.
  const [newData, setNewData] = useState({});

  return !userLength ? (
    <p>{userLength === null ? "Carregando..." : "Por favor, insira alguma cidade."}</p>
  ) : (
    <table>
      <thead>
        <tr>
          <th>Nome</th>
          <th>Cep</th>
        </tr>
      </thead>
      <tbody>
        {users.map(function (element) {
                     return <tr key={element["id"]}>
                     <td>{element["nome"]}</td>
                     <td>{element["cep"]}</td>
                   </tr>;
                  })}
      </tbody>
    </table>
  );

};

export default CidadeList;