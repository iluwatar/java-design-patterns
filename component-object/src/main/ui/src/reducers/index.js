import { combineReducers } from 'redux';
import todos from './todos';
import groceryList from './groceryList';
import visibilityFilter from './visibilityFilter';

const todoApp = combineReducers({
  todos,
  groceryList,
  visibilityFilter,
});

export default todoApp;
