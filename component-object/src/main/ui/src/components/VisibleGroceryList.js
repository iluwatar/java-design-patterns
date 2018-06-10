import { connect } from 'react-redux';
import { toggleGroceryItem } from '../actions';
import TodoList from './TodoList';

const getVisibleGroceryItems = (groceryList, filter) => {
  switch (filter) {
    case 'SHOW_ALL':
      return groceryList;
    case 'SHOW_COMPLETED':
      return groceryList.filter(t => t.completed);
    case 'SHOW_ACTIVE':
      return groceryList.filter(t => !t.completed);
    default:
      throw new Error(`Unknown filter: ${filter}.`);
  }
};

const mapStateToProps = (state) => {
  return {
    todos: getVisibleGroceryItems(state.groceryList, state.visibilityFilter),
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    onTodoClick: (id) => {
      dispatch(toggleGroceryItem(id));
    },
  };
};

const VisibleGroceryList = connect(
  mapStateToProps,
  mapDispatchToProps
)(TodoList);

export default VisibleGroceryList;
