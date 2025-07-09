import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080'; // default for dev

export const getTopics = async (): Promise<string[]> => {
  const response = await axios.get(`${API_BASE_URL}/topics`);
  return response.data;
};

export const sendMessage = async (payload: { topic: string; message: string }) => {
  await axios.post(`${API_BASE_URL}/publish`, payload);
};
