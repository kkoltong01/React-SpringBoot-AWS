import { useEffect, useState } from "react";
import Todo from "./Todo";
import AddTodo from "./AddTodo"
import { Paper,List, Container } from "@material-ui/core";
import {call} from "./service/ApiService"

function App() {
  const [items,setItems] = useState([]);



  useEffect(() => {
    call("/todo", "GET", null)
      .then((res) => {setItems(res.data)});
  },[]);

  const addItem = (item) => {
    call("/todo", "POST", item)
     .then((res) => setItems(res.data));
  };

  const editItem = () => {
    setItems([...items]);
  };

  const deleteItem = (item) => {
    call("/todo", "DELETE", item)
      .then((res) => setItems(res.data));
  };

  let todoItems = items.length > 0 && (
    <Paper>
      <List>
        {items.map((item) => (
          <Todo 
            item={item} 
            key={item.id} 
            editItem={editItem}
            deleteItem={deleteItem}
          />
        ))}
      </List>
    </Paper>
    );

  return (
    <div className="App">
      <Container maxWidth="md">
        <AddTodo addItem={addItem}/>
        <div className="TodoList">{todoItems}</div>
      </Container>
    </div>
  );
}

export default App;
