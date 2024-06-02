

import { useEffect, useState } from "react";

export const Actions = () => {
  let [users, setUsers] = useState([]);
  let [userLength, setUserLength] = useState(null);

  useEffect(() => {
    fetch("http://localhost/cadastropessoa/src/api/cidade/read.php")
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        if (data.records) {
          setUsers(data.records.reverse());
          setUserLength(true);
        } else {
          setUserLength(0);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

 const insertUser = (newUser) => {

    fetch("http://localhost:8080/api/v1/pessoa/", {
      method: "POST",
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(newUser),
    }).then((res) => {
        return res.json();
      }).then((data) => {
        if (data.id) {
          setUsers([
            {
              id: data.id,
              ...newUser,
            },
            ...users,
          ]);
          setUserLength(true);
        } else {
         alert((data.erros[0].mensagem));
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const insertContato = (newContato) => {

    
  };

  return {
    users,
    insertUser,
    userLength,
    insertContato
  };
};