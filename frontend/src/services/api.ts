const BASE_URL = 'http://localhost:8080' // Adjust this if your backend is on a different port

export async function fetchTopics(): Promise<string[]> {
  const res = await fetch(`${BASE_URL}/topics`)
  if (!res.ok) throw new Error('Failed to fetch topics')
  return res.json()
}

export async function sendMessage(topic: string, message: string): Promise<void> {
  const res = await fetch(`${BASE_URL}/send`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ topic, message }),
  })
  if (!res.ok) throw new Error('Failed to send message')
}
