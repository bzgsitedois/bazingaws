export abstract class Enum {
    public abstract readonly name: string;
    public readonly description?: string;

    public toString(): string {
        return this.name;
    }

    public static valueOf(value: string): Enum | null {
        return null;
    }

    public static values(): Enum[] {
        return [];
    }
}
