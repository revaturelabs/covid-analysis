object Runner {
    def main(args: Array[String]): Unit = {
        JsonCleaner.cleanAndSave(args(0))
    }
}

