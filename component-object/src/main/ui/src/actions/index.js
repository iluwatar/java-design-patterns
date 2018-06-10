let nextTodoId = 0;
export const addTodo = (text) => {
  return {
    type: 'ADD_TODO',
    id: (nextTodoId++).toString(),
    text,
  };
};

export const setVisibilityFilter = (filter) => {
  return {
    type: 'SET_VISIBILITY_FILTER',
    filter,
  };
};

export const toggleTodo = (id) => {
  return {
    type: 'TOGGLE_TODO',
    id,
  };
};

export const addGroceryItem = (text) => {
    return {
        type: 'ADD_GROCERY_ITEM',
        id: (nextTodoId++).toString(),
        text,
    };
};

export const toggleGroceryItem = (id) => {
    return {
        type: 'TOGGLE_GROCERY_ITEM',
        id,
    };
};
