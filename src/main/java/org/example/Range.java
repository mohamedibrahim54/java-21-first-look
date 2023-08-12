record Range(int begin, int end){
    // compact constructor
    Range{
        if (begin > end){
            throw new IllegalArgumentException("Begin is greater than End.");
        }
    }
}