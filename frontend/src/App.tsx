import './App.css'
import { useState } from 'react'
import { sendMessage } from './services/api'

function App() {
  const topics = ['topic-1', 'topic-2', 'topic-3']
  const [selectedTopic, setSelectedTopic] = useState('')
  const [message, setMessage] = useState('')
  const [successMessage, setSuccessMessage] = useState('')
  const [errorMessage, setErrorMessage] = useState('')

  const isValidTopic = (name: string) => /^[a-zA-Z0-9._-]+$/.test(name)

  const handleSend = async () => {
    if (!selectedTopic || !message.trim()) {
      setErrorMessage('Please select a topic and write a message.')
      setTimeout(() => setErrorMessage(''), 3000)
      return
    }
    if (!isValidTopic(selectedTopic)) {
      alert('Invalid topic name! Only letters, numbers, dot, underscore, and dash are allowed. No spaces.')
      return
    }

    try {
      await sendMessage(selectedTopic, message.trim())
      setSuccessMessage(`Message sent to "${selectedTopic}"!`)
      setMessage('')
      setTimeout(() => setSuccessMessage(''), 3000)
    } catch (err) {
      alert('Failed to send message. Backend error or not running.')
    }
  }

  return (
    <div className="app">
      <h1>Kafka Message Sender</h1>

      {errorMessage && (
        <div className="popup error">
          {errorMessage}
        </div>
      )}

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
