import React from 'react';
import Footer from './Footer';
import AddTodo from './AddTodo';
import VisibleTodoList from './VisibleTodoList';
import AddGroceryItem from './AddGroceryItem';
import VisibleGroceryList from './VisibleGroceryList';

const App = () => (
  <div>
    <AddTodo className="add-todo"/>
    <VisibleTodoList className="todo-list"/>
    <AddGroceryItem className="add-grocery-item"/>
    <VisibleGroceryList className="grocery-list"/>
    <Footer />
  </div>
);

export default App;
