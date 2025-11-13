import React, { Fragment, useEffect } from 'react';
import Spinner from '../layout/Spinner';
import { getPost } from '../../actions/post';
import { useSelector, useDispatch } from 'react-redux';
import { Link, useParams } from 'react-router-dom';
import PostItem from '../posts/PostItem';
import CommentForm from './CommentForm';
import CommentItem from './CommentItem';

const Post = () => {
  const dispatch = useDispatch();
  const { id } = useParams();
  const { post, loading } = useSelector((state) => state.post);
  useEffect(() => {
    dispatch(getPost(id));
  }, [id]);
  return loading || post === null ? (
    <Spinner />
  ) : (
    <Fragment>
      <Link to={'/posts'} className='btn'>
        Back To Posts
      </Link>
      <PostItem post={post} showActions={false} />
      <CommentForm postId={post._id} />
      <div className="comments">
        {post.comments.map((comment) => (
          <CommentItem key={comment._id} comment={comment} postId={post._id} />
        ))}
      </div>
    </Fragment>
  );
};
export default Post;
