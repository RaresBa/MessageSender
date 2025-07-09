import React, { useEffect, useState } from 'react';
import { getTopics, sendMessage } from '../services/api';

const MessageForm: React.FC = () => {
  const [topics, setTopics] = useState<string[]>([]);
  const [selectedTopic, setSelectedTopic] = useState('');
  const [message, setMessage] = useState('');
  const [status, setStatus] = useState('');

  useEffect(() => {
    getTopics()
      .then((data) => {
        setTopics(data);
        if (data.length > 0) {
          setSelectedTopic(data[0]);
        }
      })
      .catch(() => {
        setStatus('❌ Failed to load topics.');
      });
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!message || !selectedTopic) {
      setStatus('⚠️ Please fill in all fields.');
      return;
    }

    try {
      await sendMessage({ topic: selectedTopic, message });
      setStatus('✅ Message sent successfully!');
      setMessage('');
    } catch (err) {
      setStatus('❌ Failed to send message.');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <label htmlFor="message" className="block text-sm font-medium text-gray-700">Message</label>
        <input
          id="message"
          type="text"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500"
          placeholder="Enter your message"
        />
      </div>

      <div>
        <label htmlFor="topic" className="block text-sm font-medium text-gray-700">Select Topic</label>
        <select
          id="topic"
          value={selectedTopic}
          onChange={(e) => setSelectedTopic(e.target.value)}
          className="mt-1 block w-full px-3 py-2 border border-gray-300 bg-white rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500"
        >
          {topics.map((topic) => (
            <option key={topic} value={topic}>
              {topic}
            </option>
          ))}
        </select>
      </div>

      <button
        type="submit"
        className="w-full py-2 px-4 bg-indigo-600 text-white font-semibold rounded-md hover:bg-indigo-700 transition"
      >
        Send Message
      </button>

      {status && <p className="text-center text-sm text-gray-700 mt-2">{status}</p>}
    </form>
  );
};


export default MessageForm;