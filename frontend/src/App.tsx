import './App.css'
import { useState } from 'react'

function App() {
  const topics = ['topic-1', 'topic-2', 'topic-3']
  const [selectedTopic, setSelectedTopic] = useState('')
  const [message, setMessage] = useState('')
  const [successMessage, setSuccessMessage] = useState('')

  const handleSend = () => {
    if (!selectedTopic || !message.trim()) {
      alert('Please select a topic and write a message.')
      return
    }

    console.log('Sending message:', {
      topic: selectedTopic,
      message: message.trim(),
    })

    setSuccessMessage(`Message sent to "${selectedTopic}"!`)
    setMessage('')

    // Hide message after 3 seconds
    setTimeout(() => setSuccessMessage(''), 3000)
  }

  return (
    <div className="app">
      <h1>Kafka Message Sender</h1>

      {successMessage && (
        <div className="popup success">
          {successMessage}
        </div>
      )}

      <div className="form-group">
        <label htmlFor="topic">Select Topic:</label>
        <select
          id="topic"
          value={selectedTopic}
          onChange={(e) => setSelectedTopic(e.target.value)}
        >
          <option value="">-- Choose a topic --</option>
          {topics.map((topic) => (
            <option key={topic} value={topic}>
              {topic}
            </option>
          ))}
        </select>
      </div>

      <div className="form-group">
        <label htmlFor="message">Message:</label>
        <textarea
          id="message"
          rows={5}
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          placeholder="Write your message here..."
        />
      </div>

      <button onClick={handleSend}>Send Message</button>
    </div>
  )
}

export default App
