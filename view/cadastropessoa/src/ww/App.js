import React from 'react';

import { Provider } from "./Context";
import Cadastro from "./components/Cadastro";

import { Actions } from "./Actions";
function App() {
  const data = Actions();
  return (
    <Provider value={data}>
      <div className="App">
        <h1>Validador de Cep</h1>
        <div className="wrapper">
          <section className="left-side">
            <Cadastro />
          </section>

        </div>
      </div>
    </Provider>
  );
}

export default App;