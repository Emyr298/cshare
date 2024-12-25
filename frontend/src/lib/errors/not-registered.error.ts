export class NotRegisteredError extends Error {
  constructor(message: string) {
    super(message);
    Object.setPrototypeOf(this, NotRegisteredError.prototype);
  }
}
