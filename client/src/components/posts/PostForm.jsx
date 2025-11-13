import React, { useState } from 'react';
import { addPost } from '../../actions/post';
import { useDispatch } from 'react-redux';
import { set } from 'mongoose';

const PostForm = () => {
  const [text, setText] = useState('');
  const dispatch = useDispatch();

  return (
    <div class='post-form'>
      <div class='bg-primary p'>
        <h3>Say Something...</h3>
      </div>
      <form class='form my-1' onSubmit={(e) => {
        e.preventDefault();
        dispatch(addPost({ text }));
        setText('');
      }}>
        <textarea
          name='text'
          cols='30'
          rows='5'
          placeholder='Create a post'
          value={text}
          onChange={(e) => setText(e.target.value)}
          required
        ></textarea>
        <input type='submit' class='btn btn-dark my-1' value='Submit' />
      </form>
    </div>
  );
};

export default PostForm;
