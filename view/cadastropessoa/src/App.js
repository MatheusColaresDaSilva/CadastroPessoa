import React from 'react';

import { Provider } from "./Context";
import  Form  from "./components/Form";
import  CidadeList  from "./components/CidadeList";
import { Actions } from "./Actions";
function App() {
  const data = Actions();
  return (
    <Provider value={data}>
      <div className="App">
        <h1>Cadastro Pessoa</h1>
        <div className="wrapper">
          <section className="left-side">
            <Form />
          </section>
          <section className="right-side">
            <CidadeList />
          </section>
        </div>
      </div>
    </Provider>
  );
}

export default App;